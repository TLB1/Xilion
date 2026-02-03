package xilion.core;

import arc.func.Cons;
import arc.func.Intc;
import arc.util.Http;
import arc.util.Log;
import arc.util.serialization.Jval;
import mindustry.Vars;
import xilion.XilionJavaMod;
import xilion.ui.ChangelogDialog;

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
                           ChangelogDialog.show(releaseName, releaseTag, releaseDescription);
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
}
