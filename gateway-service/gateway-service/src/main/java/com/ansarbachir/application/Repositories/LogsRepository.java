/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.ansarbachir.application.Repositories;

import com.ansarbachir.application.Entities.Logs;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ansar
 */

@Repository
 public interface LogsRepository extends JpaRepository <Logs, UUID>{

}
