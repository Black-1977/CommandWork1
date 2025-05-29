package pro.sky.starbankrecommendations.controller.dynamic;

import org.springframework.web.bind.annotation.*;
import pro.sky.starbankrecommendations.model.dynamic.DynamicRules;
import pro.sky.starbankrecommendations.service.dynamic.DynamicRuleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping
    public DynamicRules createDynamicRule(@RequestBody DynamicRules dynamicRules) {
        return dynamicRuleService.createDynamicRule(dynamicRules);
    }

    @DeleteMapping("/{id}")
    public DynamicRules deleteDynamicRule(@RequestParam UUID id) {
        return dynamicRuleService.deleteDynamicRule(id);
    }

    @GetMapping
    public List<DynamicRules> findAll() {
        return dynamicRuleService.findAll();
    }


}
