package com.evoting.evotingsystem.service;

import com.evoting.evotingsystem.dto.CandidateResultDto;
import java.util.List;

public interface ResultService {

    List<CandidateResultDto> getResults(String constituencyId);

}