package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {
}
