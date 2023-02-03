package mpti.domain.member.dao;

import mpti.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<User, Long> {
}
