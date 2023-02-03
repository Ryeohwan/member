package mpti.domain.member.dao;

import mpti.domain.member.entity.Memo;
import mpti.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {

}
