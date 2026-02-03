package xilion.ui;

import arc.Core;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
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
    public  static void show(String releaseName, String releaseTag, String releaseDescription){
        BaseDialog dialog = new BaseDialog("New Update Available");
        Table table = new Table();
        table.defaults().left();
        table.add("Xilion Update Available!").width(getOptimalDisplayWidth()).row();
        table.add(String.format("Release %s", releaseName), Pal.accent).padBottom(16).row();
        Log.info(releaseDescription);
        table.add(ChangelogDialog.fromMarkdown(releaseDescription)).pad(10f).grow();
        dialog.cont.add(table);

        dialog.buttons.button("Disregard", dialog::remove).size(150f, 50f);
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
        }).size(150f, 50f);

        dialog.pack();
        dialog.center();

        Core.app.post(dialog::show);
    }

    public static float getOptimalDisplayWidth(){
        return Math.max(480, Core.graphics.getWidth() / Scl.scl(480) * 480 * 0.4f);
    }

    public static ScrollPane fromMarkdown(String markdown) {
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

        ScrollPane pane = new ScrollPane(content, Styles.smallPane);
        pane.setFadeScrollBars(true);
        return pane;
    }


    private static int imgCounter = 0;

    private static void addRemoteImage(Table table, String url) {
        Image image = new Image();
        image.setScaling(Scaling.fit);
        image.setFillParent(false);
        image.visible = false;
        table.add(image).width(getOptimalDisplayWidth()).height(getOptimalDisplayWidth()/2).row();

        Http.get(url)
                .error(err -> Log.err("Failed to load image: @", err))
                .submit(response -> {
                    byte[] bytes = response.getResult();

                    Core.app.post(() -> {
                        try {
                            Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
                            Texture texture = new Texture(pixmap);
                            pixmap.dispose();

                            image.setDrawable(
                                    new TextureRegionDrawable(new TextureRegion(texture))
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
