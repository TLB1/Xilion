package xilion.util;

import arc.Core;
import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Http;
import arc.util.Log;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

public class ChangelogConverter {

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
                        .width(460f)
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
        image.setSize(480, 220);
        image.visible = false;
        table.add(image).width(480).height(240).row();

        Http.get(url)
                .error(err -> Log.err("Failed to load image: @", err))
                .submit(response -> {
                    try {
                        byte[] bytes = response.getResult();

                        // Write image bytes cross‑platform
                        String fileName = "changelog_image_" + (imgCounter++) + ".png";
                        Fi tmp = Vars.tmpDirectory.child(fileName);
                        tmp.writeBytes(bytes, false); // Arc supported write method
                        Core.assets.load(tmp.name(), Texture.class).loaded = (texture) -> {
                                image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
                                image.visible = true;
                                image.invalidateHierarchy();
                            };

                    } catch (Throwable t) {
                        Log.err("Error loading remote image: @", t);
                    }
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
