package com.example.demo.mapper;

import com.example.demo.domain.Perfume;
import com.example.demo.dto.HeaderResponse;
import com.example.demo.dto.perfume.FullPerfumeResponse;
import com.example.demo.dto.perfume.PerfumeRequest;
import com.example.demo.dto.perfume.PerfumeResponse;
import com.example.demo.dto.perfume.PerfumeSearchRequest;
import com.example.demo.enums.SearchPerfume;
import com.example.demo.exception.InputFieldException;
import com.example.demo.repository.projection.PerfumeProjection;
import com.example.demo.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PerfumeMapper {

    private final CommonMapper commonMapper;
    private final PerfumeService perfumeService;

    public FullPerfumeResponse getPerfumeById(Long perfumeId) {
        return commonMapper.convertToResponse(perfumeService.getPerfumeById(perfumeId), FullPerfumeResponse.class);
    }

    public List<PerfumeResponse> getPerfumesByIds(List<Long> perfumesId) {
        return commonMapper.convertToResponseList(perfumeService.getPerfumesByIds(perfumesId), PerfumeResponse.class);
    }

    public HeaderResponse<PerfumeResponse> getAllPerfumes(Pageable pageable) {
        Page<PerfumeProjection> perfumes = perfumeService.getAllPerfumes(pageable);
        return commonMapper.getHeaderResponse(perfumes.getContent(), perfumes.getTotalPages(), perfumes.getTotalElements(), PerfumeResponse.class);
    }

    public HeaderResponse<PerfumeResponse> findPerfumesByFilterParams(PerfumeSearchRequest filter, Pageable pageable) {
        Page<PerfumeProjection> perfumes = perfumeService.findPerfumesByFilterParams(filter, pageable);
        return commonMapper.getHeaderResponse(perfumes.getContent(), perfumes.getTotalPages(), perfumes.getTotalElements(), PerfumeResponse.class);
    }

    public List<PerfumeResponse> findByPerfumer(String perfumer) {
        return commonMapper.convertToResponseList(perfumeService.findByPerfumer(perfumer), PerfumeResponse.class);
    }

    public List<PerfumeResponse> findByPerfumeGender(String perfumeGender) {
        return commonMapper.convertToResponseList(perfumeService.findByPerfumeGender(perfumeGender), PerfumeResponse.class);
    }

    public HeaderResponse<PerfumeResponse> findByInputText(SearchPerfume searchType, String text, Pageable pageable) {
        Page<PerfumeProjection> perfumes = perfumeService.findByInputText(searchType, text, pageable);
        return commonMapper.getHeaderResponse(perfumes.getContent(), perfumes.getTotalPages(), perfumes.getTotalElements(), PerfumeResponse.class);
    }

    public FullPerfumeResponse savePerfume(PerfumeRequest perfumeRequest, MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Perfume perfume = commonMapper.convertToEntity(perfumeRequest, Perfume.class);
        return commonMapper.convertToResponse(perfumeService.savePerfume(perfume, file), FullPerfumeResponse.class);
    }

    public String deletePerfume(Long perfumeId) {
        return perfumeService.deletePerfume(perfumeId);
    }
}
