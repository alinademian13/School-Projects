package lab5.catalog.web.controller;

import lab5.catalog.core.model.LabProblem;
import lab5.catalog.core.service.LabProblemService;
import lab5.catalog.web.converter.LabProblemConverter;
import lab5.catalog.web.dto.LabProblemDto;
import lab5.catalog.web.dto.LabProblemsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class LabProblemController {

    private static final Logger log = LoggerFactory.getLogger(LabProblemController.class);

    @Autowired
    private LabProblemService labProblemService;

    @Autowired
    private LabProblemConverter labProblemConverter;

    @RequestMapping(value = "/labproblems", method = RequestMethod.GET)
    LabProblemsDto getLabProblems() {
        log.trace("getLabProblems --- method entered");

        List<LabProblem> labProblems = labProblemService.getAllLabProblems();
        Set<LabProblemDto> labProblemDtos = labProblemConverter.convertModelsToDtos(labProblems);
        LabProblemsDto result = new LabProblemsDto(labProblemDtos);

        log.trace("getLabProblems: result={}", result);

        return result;
    }

    @RequestMapping(value = "/labproblems", method = RequestMethod.POST)
    LabProblemDto saveLabProblem(@RequestBody LabProblemDto dto) {
        log.trace("saveLabProblem: dto={}", dto);

        LabProblem labProblem = labProblemService.saveLabProblem(labProblemConverter.convertDtoToModel(dto));

        LabProblemDto result = labProblemConverter.convertModelToDto(labProblem);

        log.trace("saveLabProblem: result={}", result);

        return result;
    }

    @RequestMapping(value = "/labproblems/{labProblemId}", method = RequestMethod.PUT)
    LabProblemDto updateLabProblem(@PathVariable Long labProblemId, @RequestBody LabProblemDto dto) {

        log.trace("updateLabProblem: labProblemId={}, dto={}", labProblemId, dto);

        LabProblem lab = labProblemService.updateLabProblem(labProblemId, labProblemConverter.convertDtoToModel(dto));

        LabProblemDto result = labProblemConverter.convertModelToDto(lab);

        log.trace("updateLabProblem: result={}", result);

        return result;
    }

    @RequestMapping(value = "/labproblems/{labProblemId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteLabProblem(@PathVariable Long labProblemId) {
        log.trace("deleteLabProblem: labProblemId={}", labProblemId);

        labProblemService.deleteById(labProblemId);

        log.trace("deleteLabProblem --- method finished");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
