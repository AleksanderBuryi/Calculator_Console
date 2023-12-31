package interfaces;

import entity.Operation;

import java.util.List;

public interface OperationStorage {
    void save(Operation operation);
    List<Operation> findAll();
}
