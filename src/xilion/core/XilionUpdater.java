package xilion.core;

import arc.func.Cons;
import arc.func.Intc;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
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
    public static final String GET_RELEASES = "https://api.github.com/repos/TLB1/Xilion/releases";

    public static void checkUpdates() {
        Http.get(GET_LATEST_RELEASE).error(error -> {
            Log.err("Could not fetch latest release");
        }).submit(response -> {
            Jval json = Jval.read(response.getResultAsString());
            String releaseName = json.getString("name");
            String releaseTag = json.getString("tag_name");
            String releaseDescription = json.getString("body");

            if (Objects.equals(Vars.mods.getMod(XilionJavaMod.class).meta.version.toLowerCase(), releaseName.toLowerCase())) {
                Log.info("Xilion is up to date");
                return;
            }
            getMinGameVersion(releaseTag,
                    (error) -> Log.err("Could not fetch latest release details for @: @", releaseTag, error),
                    (releaseVersion) -> {
                        if (Vars.minJavaModGameVersion == releaseVersion) {
                            ChangelogDialog.showUpdate(releaseName, releaseDescription);
                        } else Log.info("Latest mod version uses a different game version");
                    });
        });
    }

    public static void getChangelog() {
        Http.get(GET_RELEASES).error(error -> {
            Log.err("Could not fetch latest releases");
        }).submit(response -> {
                    Jval json = Jval.read(response.getResultAsString());
                    Seq<Table> changelogs = new Seq<>(5);
                    for (Jval version : json.asArray()) {
                        if (changelogs.size == 5) break;
                        String releaseName = version.getString("name");
                        String releaseDescription = version.getString("body");
                        changelogs.add(ChangelogDialog.createChangeLogTable(releaseName, releaseDescription));
                    }
                    ChangelogDialog.showChangelog(changelogs);
                }
        );
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
