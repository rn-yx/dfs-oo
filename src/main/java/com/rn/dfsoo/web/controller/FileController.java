package com.rn.dfsoo.web.controller;

import com.rn.dfsoo.common.model.GlobalRet;
import com.rn.dfsoo.common.mvc.BaseController;
import com.rn.dfsoo.utils.IOUtils;
import com.rn.dfsoo.utils.MIMEUtils;
import com.rn.dfsoo.web.model.vo.FileMetaVO;
import com.rn.dfsoo.web.model.vo.FileVO;
import com.rn.dfsoo.web.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: 文件服务控制器
 *
 * @author 然诺
 * @date 2020/4/2
 */
@Api(tags = {"文件服务接口"})
@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController extends BaseController {

	private final FileService fileService;

	@ApiOperation(value = "上传文件", notes = "支持切换多种底层存储系统")
	@ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "__file")
	@PostMapping(value = "/upload")
	public GlobalRet<FileMetaVO> upload(@RequestParam MultipartFile file) {
		return success(fileService.upload(file));
	}

	@ApiOperation(value = "文件下载")
	@ApiImplicitParam(name = "fileId", value = "文件ID", required = true, example = "1")
	@GetMapping("/download/{fileId}")
	public void download(@PathVariable Long fileId) {
		FileVO fileVO = fileService.download(fileId);
		if (fileVO != null && fileVO.getFileBytes().length > 0) {
			IOUtils.download(fileVO.getFileBytes(), fileVO.getFileName());
		}
	}

	@ApiOperation(value = "文件展示")
	@ApiImplicitParam(name = "fileId", value = "文件ID", required = true)
	@GetMapping(value = "/show/{fileId}")
	public void show(@PathVariable("fileId") Long fileId) {
		FileVO fileVO = fileService.download(fileId);
		if (fileVO != null) {
			IOUtils.output(fileVO.getFileBytes(), MIMEUtils.getContentTypeBySuffix(fileVO.getSuffix()));
		}
	}

	@ApiOperation(value = "删除临时文件")
	@ApiImplicitParam(name = "fileId", value = "文件ID", required = true, example = "1")
	@DeleteMapping("/{fileId}")
	public GlobalRet deleteTempFile(@PathVariable(value = "fileId") Long fileId) {
		fileService.deleteTempFile(fileId);
		return success();
	}

}