package xilion;

import arc.Core;
import arc.func.Cons;
import arc.func.Intc;
import arc.scene.ui.layout.Table;
import arc.util.Http;
import arc.util.Log;
import arc.util.Timer;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.BaseDialog;
import xilion.util.ChangelogConverter;

import java.util.Objects;

public class XilionUpdater {
    public static final String GET_LATEST_RELEASE = "https://api.github.com/repos/TLB1/Xilion/releases/latest";
    public static final String GET_LATEST_RELEASE_DETAILS = "https://raw.githubusercontent.com/TLB1/Xilion/%s/mod.hjson";


        public static void checkUpdates() {
        Http.get(GET_LATEST_RELEASE).error(error -> {
            Log.err("Could not fetch latest release");
        }).submit(response -> {
            Jval json = Jval.read(response.getResultAsString());
            String releaseName = json.getString("name");
            String releaseTag = json.getString("tag_name");
            String releaseDescription = json.getString("body");

            if(Objects.equals(Vars.mods.getMod(XilionJavaMod.class).meta.version.toLowerCase(), releaseName.toLowerCase())){
                Log.info("Xilion is up to date");
                return;
            }
            getMinGameVersion(releaseTag,
                    (error) -> Log.err("Could not fetch latest release details for @: @", releaseTag, error),
                    (releaseVersion) -> {
                        if (Vars.minJavaModGameVersion == releaseVersion) {
                           show(releaseName, releaseTag, releaseDescription);
                        } else Log.info("Latest mod version uses a different game version");
                    });
        });
    }

    private static void getMinGameVersion(String releaseTag, Cons<Throwable> error, Intc callback) {
        Http.get(String.format(GET_LATEST_RELEASE_DETAILS, releaseTag))
                .error(error)
                .submit(response -> {
                    Jval json = Jval.read(response.getResultAsString());
                    callback.get(json.getInt("minGameVersion", -1));
                });
    }
    private static void show(String releaseName, String releaseTag, String releaseDescription){
        BaseDialog dialog = new BaseDialog("New Update Available");
        Table table = new Table();
        table.defaults().left();
        table.add("Xilion Update Available!").width(460).row();
        table.add(String.format("Release %s", releaseName), Pal.accent).padBottom(32).row();
        Log.info(releaseDescription);
        table.add(ChangelogConverter.fromMarkdown(releaseDescription)).pad(10f).grow();
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
}
