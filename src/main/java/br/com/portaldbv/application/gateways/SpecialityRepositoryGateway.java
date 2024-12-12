package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Speciality;
import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;

import java.util.List;

public interface SpecialityRepositoryGateway {

    List<Speciality> getAll();

    Speciality getById(Long id);

    Speciality getByName(String name);

    List<Speciality> getByCategory(SpecialityCategoryEnum specialityCategory);

    Speciality register(Speciality speciality);

    Speciality update(Speciality speciality);

    void delete(Speciality speciality);

}
