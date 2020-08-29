package com.revature.ers.repos;

import com.revature.ers.models.ErsReimbursement;

import java.util.Optional;
import java.util.Set;

public class ReimbRepository implements CrudRepository<ErsReimbursement> {

    @Override
    public Optional<ErsReimbursement> save(ErsReimbursement ersReimbursement) {
        return Optional.empty();
    }

    @Override
    public Set<Optional<ErsReimbursement>> findAll() {
        return null;
    }

    @Override
    public Optional<ErsReimbursement> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ErsReimbursement ersReimbursement) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
