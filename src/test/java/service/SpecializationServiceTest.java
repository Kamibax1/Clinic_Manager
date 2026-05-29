package service;

import com.example.model.dto.SpecializationDTO;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.SpecializationRepository;
import com.example.service.SpecializationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecializationServiceTest {

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private SpecializationService specializationService;

    private SpecializationEntity cardiology;
    private SpecializationEntity neurology;

    @BeforeEach
    void setUp() {
        cardiology = new SpecializationEntity(1L, "Кардиолог");
        neurology = new SpecializationEntity(2L, "Невролог");
    }

    @Test
    void findAll_shouldReturnAllSpecializations() {
        when(specializationRepository.findAll()).thenReturn(List.of(cardiology, neurology));

        List<SpecializationDTO> result = specializationService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Кардиолог");
        assertThat(result.get(1).getName()).isEqualTo("Невролог");
    }
}
