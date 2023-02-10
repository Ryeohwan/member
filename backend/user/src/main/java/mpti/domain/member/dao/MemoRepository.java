package mpti.domain.member.dao;

import mpti.domain.member.entity.Memo;
import mpti.domain.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    @Override
    <S extends Memo> S save(S entity);
    List<Memo> findAllByTrainerId(Long trainerId);
}
