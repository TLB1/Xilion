package xilion.ui;

import arc.Core;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Image;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Http;
import arc.util.Log;
import arc.util.Scaling;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

public class ChangelogDialog {
    public static void showChangelog(Seq<Table> changelogs){
        BaseDialog dialog = new BaseDialog("Xilion Changelog");
        dialog.addCloseButton();

        Table content = new Table();
        content.center();
        ScrollPane pane = new ScrollPane(content);
        for(Table table : changelogs){
            content.add(table).row();
        }
        dialog.cont.add(pane).row();
        dialog.pack();
        dialog.center();
        Core.app.post(dialog::show);
    }
    public static Table createChangeLogTable(String releaseName, String releaseDescription){
        Table table = new Table();
        table.defaults().left();
        table.add(String.format("Release %s", releaseName), Pal.accent).padTop(32).padBottom(16).row();
        table.add(ChangelogDialog.fromMarkdown(releaseDescription));
        table.add().pad(10f).grow();
        return table;
    }

    public  static void showUpdate(String releaseName, String releaseDescription){
        BaseDialog dialog = new BaseDialog("New Update Available");
        Table table = new Table();
        table.defaults().left();
        table.add("Xilion Update Available!").width(getOptimalDisplayWidth()).row();
        table.add(String.format("Release %s", releaseName), Pal.accent).padBottom(16).row();
        Table changes = ChangelogDialog.fromMarkdown(releaseDescription);
        ScrollPane pane = new ScrollPane(changes, Styles.smallPane);
        pane.setFadeScrollBars(true);
        table.add(pane).pad(10f).grow();
        dialog.cont.add(table);


        dialog.buttons.button("Disregard", dialog::remove).width(getOptimalDisplayWidth()/2).height(50);
        dialog.buttons.button("Update now", () -> {
            try {
                dialog.remove();
                Vars.ui.mods.show();
                Vars.ui.mods.githubImportMod("TLB1/Xilion", true);
                Vars.ui.mods.toFront();
                Timer.schedule(() -> Vars.ui.loadfrag.toFront(), 0.2f);
            } catch (Throwable e) {
                Log.err(e);
            }
        }).width(getOptimalDisplayWidth()/2).height(50);

        dialog.pack();
        dialog.center();
        Core.app.post(dialog::show);
    }

    public static float getOptimalDisplayWidth(){
        return Math.max(480, Core.graphics.getWidth() / Scl.scl(480) * 480 * 0.4f);
    }

    public static Table fromMarkdown(String markdown) {
        Table content = new Table();
        content.left();
        content.defaults().left().padBottom(4);

        markdown = markdown.replace("\r\n", "\n");

        String currentSection = null;

        for (String line : markdown.split("\n")) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Section header
            if (line.startsWith("## ")) {
                currentSection = line.substring(3).trim();

                content.add(currentSection, Pal.accent).padTop(10).row();
                content.image(Tex.windowEmpty).color(Pal.accent).height(3f).growX().padBottom(6).row();
                continue;
            }

            // Bullet point
            if (line.startsWith("- ")) {
                content.add("• " + line.substring(2).trim())
                        .wrap()
                        .width(getOptimalDisplayWidth() - 20)
                        .padLeft(10)
                        .row();
                continue;
            }

            // Image
            if (line.startsWith("<img")) {
                String src = extractImageSrc(line);
                if (src != null) {
                    Log.info("Adding image from " + src);
                    addRemoteImage(content, src);
                }
            }
        }

        return content;
    }

    private static void addRemoteImage(Table table, String url) {
        Image image = new Image();
        image.setScaling(Scaling.fit);
        image.setFillParent(false);
        image.visible = false;
        table.add(image).width(getOptimalDisplayWidth()).height(getOptimalDisplayWidth()/2).row();

        Http.get(url)
                .error(err -> Log.err("Failed to load image: @", err))
                .submit(response -> {
                    Pixmap pixmap = new Pixmap(response.getResult());
                    Core.app.post(() -> {
                        try {
                            Texture texture = new Texture(pixmap);
                            texture.setFilter(Texture.TextureFilter.linear);
                            pixmap.dispose();

                            image.setDrawable(
                                    new TextureRegion(texture)
                            );
                            image.visible = true;
                            image.invalidateHierarchy();

                        } catch (Throwable t) {
                            Log.err("Error loading remote image: @", t);
                        }
                    });
                });
    }


        private static String extractImageSrc(String imgTag) {
        int i = imgTag.indexOf("src=\"");
        if (i == -1) return null;

        int start = i + 5;
        int end = imgTag.indexOf('"', start);
        if (end == -1) return null;

        return imgTag.substring(start, end);
    }
}
