package core.basesyntax.dao;

import core.basesyntax.model.User;

public interface StorageDao {
    User add(User user);
    User get(String login);

    // Dodana metoda do czyszczenia magazynu
    void clear();
}