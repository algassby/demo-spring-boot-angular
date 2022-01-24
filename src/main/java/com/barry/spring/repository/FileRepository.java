/**
 * 
 */
package com.barry.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barry.spring.model.FileInfo;

/**
 * @author algas
 *
 */
@Repository
public interface FileRepository extends JpaRepository<FileInfo, String>{

}
