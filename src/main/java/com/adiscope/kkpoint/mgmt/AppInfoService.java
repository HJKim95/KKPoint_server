package com.adiscope.kkpoint.mgmt;

import com.adiscope.kkpoint.common.Header;
import com.adiscope.kkpoint.common.custom_exception.UnauthorizedVersionCheckException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppInfoService {

    private final AppInfoRepository appInfoRepository;

    @Transactional
    public Header<AppInfoResponse> readVersion(String os) throws IllegalAccessException {
        Optional<AppInfo> optional = appInfoRepository.findById(1L);
        if (os.equals("ios")) {
            return optional
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseIos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else if (os.equals("aos")) {
            return optional
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseAos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else {
            throw new UnauthorizedVersionCheckException();
        }

    }

    @Transactional
    public Header<AppInfoResponse> updateMinVersion(String os, String version) throws IllegalAccessException {
        Optional<AppInfo> optional = appInfoRepository.findById(1L);
        if (os.equals("ios")) {
            return optional.map(appInfo -> {
                appInfo.setIosMinVersion(version);
                return appInfo;
            })
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseIos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else if (os.equals("aos")) {
            return optional.map(appInfo -> {
                appInfo.setAosMinVersion(version);
                return appInfo;
            })
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseAos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else {
            throw new UnauthorizedVersionCheckException();
        }
    }

    @Transactional
    public Header<AppInfoResponse> updateMarketVersion(String os, String version) throws IllegalAccessException {
        Optional<AppInfo> optional = appInfoRepository.findById(1L);
        if (os.equals("ios")) {
            return optional.map(appInfo -> {
                appInfo.setIosMarketVersion(version);
                return appInfo;
            })
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseIos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else if (os.equals("aos")) {
            return optional.map(appInfo -> {
                appInfo.setAosMarketVersion(version);
                return appInfo;
            })
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseAos(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));
        } else {
            throw new UnauthorizedVersionCheckException();
        }
    }

    @Transactional
    public Header<RegularTestResponse> updateRegularTest(String pwd, RegularTestRequest request) throws IllegalAccessException {

        if (pwd.equals("kkpoint-ios")) {
            Optional<AppInfo> optional = appInfoRepository.findById(1L);
            return optional.map(appInfo -> {
                appInfo.setRegularTestMessage(request.getRegularTestMessage())
                        .setRegularTestStartDate(request.getRegularTestStart())
                        .setRegularTestEndDate(request.getRegularTestEnd());
                return appInfo;
            })
                    .map(appInfo -> appInfoRepository.save(appInfo))
                    .map(appInfo -> responseRegularTest(appInfo))
                    .orElseGet(()->Header.ERROR("데이터 오류"));

        } else {
            throw new UnauthorizedVersionCheckException();
        }

    }

    private Header<AppInfoResponse> responseIos(AppInfo appInfo) {
        AppInfoResponse appInfoResponse = AppInfoResponse.builder()
                .minVersion(appInfo.getIosMinVersion())
                .marketVersion(appInfo.getIosMarketVersion())
                .regularTestMessage(appInfo.getRegularTestMessage())
                .regularTestStartDate(appInfo.getRegularTestStartDate())
                .regularTestEndDate(appInfo.getRegularTestEndDate())
                .build();

        // Header + data return
        return Header.OK(appInfoResponse);
    }

    private Header<AppInfoResponse> responseAos(AppInfo appInfo) {
        AppInfoResponse appInfoResponse = AppInfoResponse.builder()
                .minVersion(appInfo.getAosMinVersion())
                .marketVersion(appInfo.getAosMarketVersion())
                .regularTestMessage(appInfo.getRegularTestMessage())
                .regularTestStartDate(appInfo.getRegularTestStartDate())
                .regularTestEndDate(appInfo.getRegularTestEndDate())
                .build();

        // Header + data return
        return Header.OK(appInfoResponse);
    }

    private Header<RegularTestResponse> responseRegularTest(AppInfo appInfo) {
        RegularTestResponse regularTestResponse = RegularTestResponse.builder()
                .regularTestMessage(appInfo.getRegularTestMessage())
                .regularTestStart(appInfo.getRegularTestStartDate())
                .regularTestEnd(appInfo.getRegularTestEndDate())
                .build();

        // Header + data return
        return Header.OK(regularTestResponse);
    }



}