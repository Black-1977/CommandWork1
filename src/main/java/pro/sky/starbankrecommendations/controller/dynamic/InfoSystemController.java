package pro.sky.starbankrecommendations.controller.dynamic;

import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.starbankrecommendations.model.InfoSystem;

@RestController
public class InfoSystemController {
    private final BuildProperties buildProperties;
    private InfoSystem infoSystem;

    public InfoSystemController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @GetMapping("/management/info")
    public InfoSystem getInfoSystem() {
        return new InfoSystem(buildProperties.getName(), buildProperties.getVersion());
    }

}
