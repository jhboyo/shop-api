package com.app.shop.entity;

import com.app.shop.constant.Role;
import com.app.shop.dto.MemberFormDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(MemberFormDto memberFormDto) {
        this.name = memberFormDto.getName();
        this.email = memberFormDto.getEmail();
        this.password = memberFormDto.getPassword();
        this.address = memberFormDto.getAddress();
        this.role = Role.USER;
    }
}
