package com._404s.attireflow.inventory.init;

import com._404s.attireflow.inventory.repo.VariantRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private final VariantRepository variantRepository;
    private final DataSource dataSource;

    public DatabaseSeeder(VariantRepository variantRepository, DataSource dataSource) {
        this.variantRepository = variantRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (variantRepository.count() > 0) {
            return; // DB already has data → don't seed again
        }

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("seed-data.sql"));
        populator.execute(dataSource);
    }
}
