package org.egovframe.sample.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sample")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 100, message = "이름은 100자를 초과할 수 없습니다.")
    @Column(length = 100, nullable = false)
    private String name;

    @Size(max = 4000, message = "설명은 4000자를 초과할 수 없습니다.")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "사용여부는 필수 입력값입니다.")
    @Pattern(regexp = "^[YN]$", message = "사용여부는 Y 또는 N만 입력 가능합니다.")
    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn;

    @Size(max = 100, message = "등록자는 100자를 초과할 수 없습니다.")
    @Column(name = "reg_user", length = 100)
    private String regUser;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 