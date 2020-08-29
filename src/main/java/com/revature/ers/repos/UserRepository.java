package com.revature.ers.repos;

import com.revature.ers.models.ErsUser;

import java.util.Optional;
import java.util.Set;

public class UserRepository implements CrudRepository<ErsUser>{
    @Override
    public Optional<ErsUser> save(ErsUser ersUser) {
        return Optional.empty();
    }

    @Override
    public Set<Optional<ErsUser>> findAll() {
        return null;
    }

    @Override
    public Optional<ErsUser> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ErsUser ersUser) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
