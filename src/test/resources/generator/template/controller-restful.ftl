package ${basePackage}.web;

import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${responseResultPackage};
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with ${createBy}
 * Description:
 * @author  ${author}
 * Date: ${date}
 * Time: ${time}
 */
@ResponseResult
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping
    public ${modelNameUpperCamel} add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ${modelNameLowerCamel};
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ${modelNameLowerCamel}Service.deleteById(id);
    }

    @PutMapping
    public ${modelNameUpperCamel} update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ${modelNameLowerCamel};
    }

    @GetMapping("/{id}")
    public ${modelNameUpperCamel} detail(@PathVariable Long id) {
        return ${modelNameLowerCamel}Service.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
         return new PageInfo(list);
    }
}
