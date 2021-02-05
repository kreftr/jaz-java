package pl.edu.pjwstk.jaz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.jaz.models.Parameter;
import pl.edu.pjwstk.jaz.repository.ParameterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterService {

    private ParameterRepository parameterRepository;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository){
        this.parameterRepository = parameterRepository;
    }

    public void addParameter(Parameter parameter){
        parameterRepository.add(parameter);
    }

    public void removeParameter(Parameter parameter){
        parameterRepository.remove(parameter);
    }

    public void editParameter(Parameter parameter){
        parameterRepository.edit(parameter);
    }

    public Optional<Parameter> findParameterById(Long id){
        return parameterRepository.findById(id);
    }

    public Optional<Parameter> findParameterByKey(String key){
        return parameterRepository.findByKey(key);
    }

    public Optional<List<Parameter>> findAllParameters(){ return parameterRepository.findAll(); }
}
