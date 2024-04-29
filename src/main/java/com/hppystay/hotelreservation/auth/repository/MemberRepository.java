package com.hppystay.hotelreservation.auth.repository;

import com.hppystay.hotelreservation.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    boolean existsByEmail(String email);

    List<Member> findByEmailContainingOrNicknameContaining(String email, String nickname);

    // 더미데이터를 위해 만든 repo
    List<Member> findByNicknameIn(List<String> nicknames);

}
