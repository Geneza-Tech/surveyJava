package com.survey.geneza.web.rest; 
import com.survey.geneza.domain.GridDisplay;
import com.survey.geneza.service.GridDisplayService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("GridDisplayRestController")
public class GridDisplayRestController {


    @Autowired
    private GridDisplayService gridDisplayService;

    @RequestMapping(value = "/GridDisplay/GridDisplayByTag/{gridDisplay_tag}/Param1/{param1}", method = RequestMethod.GET)
    @ResponseBody
    public GridDisplay getAllByMenuListId(@PathVariable("gridDisplay_tag") String gridListTag,@PathVariable("param1") Integer param1) {
        return gridDisplayService.getListItems(gridListTag, param1, 0, 0);
    }
    
    @RequestMapping(value = "/GridDisplay/GridDisplayByTag/{gridDisplay_tag}/Param1/{param1}/Param2/{param2}", method = RequestMethod.GET)
    @ResponseBody
    public GridDisplay getAllByMenuListId(@PathVariable("gridDisplay_tag") String gridListTag,@PathVariable("param1") Integer param1,@PathVariable("param2") Integer param2) {
        System.out.println(param2);
        return gridDisplayService.getListItems(gridListTag, param1, param2, 0);
    }
    
    @RequestMapping(value = "/GridDisplay/GridDisplayByTag/{gridDisplay_tag}/Param1/{param1}/Param2/{param2}/Param3/{param3}", method = RequestMethod.GET)
    @ResponseBody
    public GridDisplay getAllByMenuListId(@PathVariable("gridDisplay_tag") String gridListTag,@PathVariable("param1") Integer param1,@PathVariable("param2") Integer param2,@PathVariable("param3") Integer param3) {
        System.out.println(param2);
        return gridDisplayService.getListItems(gridListTag, param1, param2, param3);
    }

}