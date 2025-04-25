package com.personal.urlshortener.repos;

import com.personal.urlshortener.models.IpInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpInfoRepository extends JpaRepository<IpInfo, Long> {


}
