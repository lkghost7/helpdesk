package itacademy.services;

import itacademy.domain.entity.Branch;
import itacademy.dto.BranchDto;

public class BranchServiceImpl implements BranchService {
  private static final Object LOCK = new Object();
  private static BranchServiceImpl INSTANCE = null;

  public static BranchServiceImpl getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        if (INSTANCE == null) {
          INSTANCE = new BranchServiceImpl();
        }
      }
    }
    return INSTANCE;
  }

  private BranchServiceImpl() {
  }

  @Override
  public BranchDto mapperBranch(Branch branch) {
    return new BranchDto(branch.getId(), branch.getName(), branch.getAddress()
    );
  }
}
