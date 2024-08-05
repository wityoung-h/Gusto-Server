package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
    @Query("SELECT ic.code FROM InvitationCode ic WHERE ic.group = :group")
    String findCodeByGroup(Group group);
  
    Optional<InvitationCode> findInvitationCodeByGroup(Group group);
}