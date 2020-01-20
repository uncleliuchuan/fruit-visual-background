package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.FruitService;
import com.jclz.fruit.utils.QiniuCloudUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@Slf4j
@Api(tags = "水果api")
@RestController
@RequestMapping("/fruit")
public class FruitController {
    @Autowired
    FruitService fruitService;
    /**
     * 水果列表
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "水果列表")
    @ResponseBody
    public Map<String, Object> queryFruitList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                              @ApiParam(required = false, value = "水果类型") @RequestParam(required = false,value = "fruitType") Integer fruitType,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size) {
        Page<Fruit> fruitList = fruitService.queryFruitList(fruitName,fruitType, current, size);

        return GenResult.SUCCESS.genResult(fruitList);
    }
    /**
     * 添加水果
     *
     * @param fruit 水果信息
     * @return
     */
    @PostMapping("/addFruit")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "添加水果")
    public Map<String, Object> addFruit(@ApiParam(value = "水果") Fruit fruit,
                                       @ApiParam(value = "首页图片", required = false) @RequestParam(name = "fruitPicture", required = false) MultipartFile fruitPicture,
                                       @ApiParam(value = "详情页轮播图", required = false) @RequestParam(name = "fruitDetailCarousels", required = false) MultipartFile[] fruitDetailCarousels,
                                       @ApiParam(value = "水果图片详情", required = false) @RequestParam(name = "fruitDetails", required = false) MultipartFile[] fruitDetails) {
        if (fruit == null && fruitPicture.isEmpty() && fruitDetailCarousels == null && fruitDetails == null) {
            return GenResult.PARAMS_ERROR.genResult();
        }
        String fruitPictureUrl = "";
        StringBuffer fruitDetailCarouselUrl = new StringBuffer("");
        if (fruitDetailCarousels != null) {
            for (int i = 0; i < fruitDetailCarousels.length; i++) {
                if (!fruitDetailCarousels[i].isEmpty()) {
                    if (i != 0) fruitDetailCarouselUrl.append(";");
                    fruitDetailCarouselUrl.append(QiniuCloudUtil.uploadUtil(fruitDetailCarousels[i]));
                }
            }
        }
        StringBuffer fruitDetailUrl = new StringBuffer("");
        if (fruitDetails != null) {
            for (int i = 0; i < fruitDetails.length; i++) {
                if (!fruitDetails[i].isEmpty()) {
                    if (i != 0) fruitDetailUrl.append(";");
                    fruitDetailUrl.append(QiniuCloudUtil.uploadUtil(fruitDetails[i]));
                }
            }
        }
        if (fruitPicture != null && !fruitPicture.isEmpty()) {
            fruitPictureUrl = QiniuCloudUtil.uploadUtil(fruitPicture);
        }
        fruit.setFruitPictureUrl(fruitPictureUrl);
        fruit.setFruitDetail(fruitDetailUrl.toString());
        fruit.setFruitDetailCarousel(fruitDetailCarouselUrl.toString());
        return fruitService.addFruit(fruit);

    }

    /**
     * 查找水果
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/selectFruit")
    @ApiOperation(httpMethod = "GET", response = Map.class, responseContainer = "Map", value = "查找水果")
    @ResponseBody
    public Map<String, Object> selectFruit(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "id") Integer id) {
        return fruitService.selectFruit(id);
    }

    /**
     * 更新水果
     *
     * @param fruit 水果信息
     * @return
     */
    @PostMapping("/updateFruit")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新水果")
    public Map<String, Object> updateFruit(@ApiParam(value = "水果") Fruit fruit,
                                           @ApiParam(value = "首页图片", required = false) @RequestParam(name = "fruitPicture", required = false) MultipartFile fruitPicture,
                                           @ApiParam(value = "详情页轮播图", required = false) @RequestParam(name = "fruitDetailCarousels", required = false) MultipartFile[] fruitDetailCarousels,
                                           @ApiParam(value = "水果图片详情", required = false) @RequestParam(name = "fruitDetails", required = false) MultipartFile[] fruitDetails) {
        String fruitPictureUrl = "";
        StringBuffer fruitDetailCarouselUrl = new StringBuffer("");
        if (fruitDetailCarousels != null) {
            for (int i = 0; i < fruitDetailCarousels.length; i++) {
                if (!fruitDetailCarousels[i].isEmpty()) {
                    if (i != 0) fruitDetailCarouselUrl.append(";");
                    fruitDetailCarouselUrl.append(QiniuCloudUtil.uploadUtil(fruitDetailCarousels[i]));
                }
            }
        }
        StringBuffer fruitDetailUrl = new StringBuffer("");
        if (fruitDetails != null) {
            for (int i = 0; i < fruitDetails.length; i++) {
                if (!fruitDetails[i].isEmpty()) {
                    if (i != 0) fruitDetailUrl.append(";");
                    fruitDetailUrl.append(QiniuCloudUtil.uploadUtil(fruitDetails[i]));
                }
            }
        }
        if (fruitPicture != null && !fruitPicture.isEmpty()) {
            fruitPictureUrl = QiniuCloudUtil.uploadUtil(fruitPicture);
        }
        fruit.setFruitPictureUrl(fruitPictureUrl);
        fruit.setFruitDetail(fruitDetailUrl.toString());
        fruit.setFruitDetailCarousel(fruitDetailCarouselUrl.toString());
        return fruitService.updateFruit(fruit);

    }

    /**
     * 删除水果
     *
     * @param
     * @param
     * @return
     */
    /*@PostMapping("/deleteFruit")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "删除水果")
    @ResponseBody
    public Map<String, Object> deleteFruit(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "id") Integer id) {
        return fruitService.deleteFruit(id);
    }*/
    /**
     * 下架或上架水果
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/lowerShelfFruit")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "下架或上架水果")
    @ResponseBody
    public Map<String, Object> lowerShelfFruit(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "id") Integer id) {
        return fruitService.lowerShelfFruit(id);
    }
}
