package service;

import domain.LabProblem;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.HashSet;
import java.util.Set;

public class LabProblemService {

    private Repository<Long, LabProblem> labProblemRepo;

    public LabProblemService(Repository<Long, LabProblem> labProblemRepo) {
        this.labProblemRepo = labProblemRepo;
    }

    public void addLabProblem(LabProblem lab) throws ValidatorException {
        this.labProblemRepo.save(lab);
    }

    public void removeLabProblem(Long id) {
        this.labProblemRepo.delete(id);
    }

    public Set<LabProblem> getAllLabProblems() {
        Set<LabProblem> labs = new HashSet<>();
        this.labProblemRepo.findAll().forEach(labs::add);

        return labs;
    }

    public void updateLabProblem(LabProblem labProblem) throws ValidatorException {
        this.labProblemRepo.update(labProblem);
    }
}
