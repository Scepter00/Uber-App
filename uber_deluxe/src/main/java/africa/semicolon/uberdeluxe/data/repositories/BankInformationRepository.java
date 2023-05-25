package africa.semicolon.uberdeluxe.data.repositories;

import africa.semicolon.uberdeluxe.data.models.BankInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankInformationRepository extends JpaRepository<BankInformation, Long> {
}
