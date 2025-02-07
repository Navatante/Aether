package org.jonatancarbonellmartinez.domain.repository.contract;

public interface DatabaseTransactionManager {
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
    boolean isTransactionActive();
}
