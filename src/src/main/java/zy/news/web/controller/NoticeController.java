package zy.news.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zy.news.common.Page;
import zy.news.web.bean.Notice;
import zy.news.web.bean.NoticeSimple;
import zy.news.web.service.INotice;
import zy.news.web.ui.param.ReviewStatus;
import zy.news.web.ui.result.ReviewInfo;
import zy.news.web.zsys.bean.*;

import javax.servlet.http.HttpSession;

/**
 * 通告管理
 *
 * @author maoko
 * @date 2020/3/2 10:56
 */
@RestController
@RequestMapping("/notice")
@ExcuteControllerDsrc("通告管理")
public class NoticeController {
    private final INotice noticeService;

    @Autowired
    public NoticeController(INotice noticeService) {
        this.noticeService = noticeService;
    }


    //region 通告发布

    @GetMapping("existTitle")
    @ExcuteInterfaceDsrc("标题是否重复")
    @ExcutePermission
    public boolean existTitle(@RequestParam String title) throws Exception {
        Notice notice = new Notice();
        notice.setTitle(title);
        return noticeService.exist(notice);
    }

    @PostMapping("addNotice")
    @ExcuteInterfaceDsrc("添加通告")
    @ExcutePermission
    public void addNotice(HttpSession session, @RequestBody Notice notice) throws Exception {
        noticeService.add(session, notice);
    }

    @PostMapping("updateNotice")
    @ExcuteInterfaceDsrc("更新通告")
    @ExcutePermission
    public void updateNotice(HttpSession session, @RequestBody Notice notice) throws Exception {
        noticeService.update(session, notice);
    }

    @GetMapping("getReviewInfo")
    @ExcuteInterfaceDsrc("获取审核详情")
    @ExcutePermission
    public ReviewInfo getReviewInfo(HttpSession session, @RequestParam Long noticeid) throws Exception {
        return noticeService.getReviewComment(noticeid);
    }

    @PostMapping("deleteNotice")
    @ExcuteInterfaceDsrc("删除通告")
    @ExcutePermission
    public void deleteNotice(HttpSession session, @RequestBody Notice notice) throws Exception {
        noticeService.delete(session, notice);
    }

    @PostMapping("getPublishNotices")
    @ExcuteInterfaceDsrc("获取已发布通告列表")
    @ExcutePermission
    public PageValuesResult<NoticeSimple> getPublishNotice(@RequestBody Page page) throws Exception {
        return noticeService.getNotice(page, ReviewStatus.所有);
    }

    @GetMapping("getNoticeDetail")
    @ExcuteInterfaceDsrc("获取通告详情")
    @ExcutePermission(userType = ExcuteUserType.游客)
    public Notice getNoticeDetail(@RequestParam Long noticeid) throws Exception {
        return noticeService.getRecordDetail(noticeid);
    }


    //endregion

    //region 通告审核

    @PostMapping("getNotice")
    @ExcuteInterfaceDsrc("获取已审核通告列表")
    @ExcutePermission(userType = ExcuteUserType.游客)
    public PageValuesResult<NoticeSimple> getNotice(@RequestBody Page page) throws Exception {
        return noticeService.getNotice(page, ReviewStatus.已审核);
    }

    @PostMapping("getNoticeUnReview")
    @ExcuteInterfaceDsrc("获取未审核通告列表")
    @ExcutePermission
    public PageValuesResult<NoticeSimple> getNoticeUnReview(@RequestBody Page page) throws Exception {
        return noticeService.getNotice(page, ReviewStatus.未审核);
    }

    @PostMapping("reviewNotice")
    @ExcuteInterfaceDsrc("审核指定通告")
    @ExcutePermission
    public void reviewNotice(HttpSession session, @RequestBody ReviewInfo reviewInfo) throws Exception {
        noticeService.review(session, reviewInfo);
    }

    //endregion
}
