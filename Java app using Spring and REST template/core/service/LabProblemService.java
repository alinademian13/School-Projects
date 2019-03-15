package lab5.catalog.core.service;

import lab5.catalog.core.model.LabProblem;

import java.util.List;

public interface LabProblemService {

    List<LabProblem> getAllLabProblems();

    LabProblem saveLabProblem(LabProblem labProblem);

    LabProblem updateLabProblem(Long id, LabProblem labProblem);

    void deleteById(Long id);
}
