package africa.semicolon.uberdeluxe.data.repositories;

import africa.semicolon.uberdeluxe.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository  extends JpaRepository<Admin, Long> {
}
