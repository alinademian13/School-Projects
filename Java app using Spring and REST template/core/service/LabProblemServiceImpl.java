package lab5.catalog.core.service;

import lab5.catalog.core.model.LabProblem;
import lab5.catalog.core.repository.LabProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LabProblemServiceImpl implements LabProblemService {

    private static final Logger log = LoggerFactory.getLogger(LabProblemService.class);

    @Autowired
    private LabProblemRepository labProblemRepository;

    @Override
    public List<LabProblem> getAllLabProblems() {
        log.trace("getAllLabProblems --- method entered");

        List<LabProblem> result = labProblemRepository.findAll();

        log.trace("getAllLabProblems: result={}", result);

        return result;
    }

    @Override
    public LabProblem saveLabProblem(LabProblem labProblem) {
        return labProblemRepository.save(labProblem);
    }

    @Override
    @Transactional
    public LabProblem updateLabProblem(Long id, LabProblem labProblem) {

        log.trace("updateLabProblem: id={}, labProblem={}", id, labProblem);

        Optional<LabProblem> optional = labProblemRepository.findById(id);

        LabProblem result = optional.orElse(labProblem);
        result.setDiscipline(labProblem.getDiscipline());
        result.setLabNumber(labProblem.getLabNumber());

        log.trace("updateLabProblem: result={}", result);

        return result;
    }

    @Override
    public void deleteById(Long id) {
        log.trace("deleteById: labProblemId={}", id);

        labProblemRepository.deleteById(id);

        log.trace("deleteById --- method finished");
    }
}
