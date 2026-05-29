package service;

import com.example.model.dto.StatusDTO;
import com.example.model.entity.StatusEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.StatusRepository;
import com.example.service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    private StatusEntity pendingStatus;
    private StatusEntity completedStatus;

    @BeforeEach
    void setUp() {
        pendingStatus = new StatusEntity(1L, StatusEnum.PENDING);
        completedStatus = new StatusEntity(2L, StatusEnum.COMPLETED);
    }

    @Test
    void findById_shouldReturnStatusDTO_whenExists() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(pendingStatus));

        Optional<StatusDTO> result = statusService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(StatusEnum.PENDING);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(statusRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<StatusDTO> result = statusService.findById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllStatuses() {
        when(statusRepository.findAll()).thenReturn(List.of(pendingStatus, completedStatus));

        List<StatusDTO> result = statusService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findByStatus_shouldReturnStatusDTO() {
        when(statusRepository.findByStatus(StatusEnum.PENDING)).thenReturn(pendingStatus);

        StatusDTO result = statusService.findByStatus(StatusEnum.PENDING);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
    }
}