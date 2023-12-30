package mash.masharium.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository {

    default String getErrorMassage(){
        return "Сущность не найдена";
    }
}
