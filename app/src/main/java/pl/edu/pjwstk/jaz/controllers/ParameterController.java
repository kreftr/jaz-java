package pl.edu.pjwstk.jaz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.jaz.models.Parameter;
import pl.edu.pjwstk.jaz.services.ParameterService;

import java.util.List;


@RestController
@RequestMapping(value = "/parameter")
public class ParameterController {

    private ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService parameterService){
        this.parameterService = parameterService;
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PostMapping
    public ResponseEntity addParameter(@RequestParam(name = "key") String key){
        if(parameterService.findParameterByKey(key).isEmpty()){
            if(!(key.isEmpty() && key.isBlank())){
                Parameter parameter = new Parameter(key);
                parameterService.addParameter(parameter);
                return new ResponseEntity("Parameter created", HttpStatus.CREATED);
            }
            else return new ResponseEntity("Parameter key cannot be empty or blank", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity("Parameter with this key already exists", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity removeParameter(@PathVariable(name = "id") Long id){

        if(!parameterService.findParameterById(id).isEmpty()){

            Parameter parameter = parameterService.findParameterById(id).get();
            parameterService.removeParameter(parameter);
            return new ResponseEntity("Parameter removed", HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PutMapping(path = "/{id}")
    public ResponseEntity editParameter(@PathVariable(name = "id") Long id, @RequestParam(name = "newKey") String newKey){

        if(!parameterService.findParameterById(id).isEmpty()){
            if(!(newKey.isEmpty() || newKey.isBlank())){

                Parameter parameter = parameterService.findParameterById(id).get();
                parameter.setKey(newKey);
                parameterService.editParameter(parameter);

                return new ResponseEntity("Parameter edited", HttpStatus.OK);
            }
            else return new ResponseEntity("Parameter cannot be empty or blank", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity getParameters(){
        List<Parameter> result = parameterService.findAllParameters().get();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
