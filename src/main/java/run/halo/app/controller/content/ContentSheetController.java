package run.halo.app.controller.content;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import run.halo.app.model.entity.Sheet;
import run.halo.app.model.enums.PostStatus;
import run.halo.app.service.SheetService;
import run.halo.app.service.ThemeService;

/**
 * @author ryanwang
 * @date : 2019-03-21
 */
@Controller
public class ContentSheetController {


    private final SheetService sheetService;

    private final ThemeService themeService;

    public ContentSheetController(SheetService sheetService,
                                  ThemeService themeService) {
        this.sheetService = sheetService;
        this.themeService = themeService;
    }

    /**
     * Render photo page
     *
     * @return template path: themes/{theme}/gallery.ftl
     */
    @GetMapping(value = "/gallery")
    public String gallery() {
        return themeService.render("gallery");
    }

    /**
     * Render links page
     *
     * @return template path: themes/{theme}/links.ftl
     */
    @GetMapping(value = "/links")
    public String links() {
        return themeService.render("links");
    }

    /**
     * Render custom sheet
     *
     * @param url   sheet url
     * @param model model
     * @return template path: themes/{theme}/sheet.ftl
     */
    @GetMapping(value = "/s/{url}")
    public String sheet(@PathVariable(value = "url") String url,
                        @RequestParam(value = "cp", defaultValue = "1") Integer cp,
                        Model model) {
        Sheet sheet = sheetService.getBy(PostStatus.PUBLISHED, url);

        model.addAttribute("sheet", sheetService.convertToDetail(sheet));

        if (StrUtil.isNotEmpty(sheet.getTemplate())) {
            return themeService.render(sheet.getTemplate());
        }
        return themeService.render("sheet");
    }
}
