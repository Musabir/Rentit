package com.rentit.inventory.domain.repository;

import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;


public class PlantInventoryEntryRepositoryImpl implements CustomPlantInventoryEntryRepository {
    @Autowired
    EntityManager em;



    @Override
    public List<PlantInventoryEntry> findAvailableByName(String name, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("select e from PlantInventoryItem i join i.plantInfo e where i.equipmentCondition = 'SERVICEABLE' AND lower(e.name) like :name" +
                " AND NOT EXISTS (select r from PlantReservation r where r.plant = i and :startDate <= r.schedule.endDate and :endDate >= r.schedule.startDate)", PlantInventoryEntry.class)
                .setParameter("name", "%"+ name.toLowerCase() +"%")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }



    @Override
    public PlantInventoryEntry findAvailableById(String id, LocalDate startDate, LocalDate endDate) {
        return em.createQuery("select e from PlantInventoryItem i join i.plantInfo e where i.equipmentCondition = 'SERVICEABLE' AND e.id = :id " +
                " AND NOT EXISTS (select r from PlantReservation r where r.plant = i and :startDate <= r.schedule.endDate and :endDate >= r.schedule.startDate)", PlantInventoryEntry.class)
                .setParameter("id", id)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }






}
