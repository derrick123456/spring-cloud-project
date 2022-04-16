/**************初始化数据示例,请加入EXISTS判断,否则会有问题,仅支持非多租户自动创建表,自动初始化脚本***************/
INSERT INTO wms_abc (id,NAME)  SELECT '10e1041adf6311e98af2005056b6b8b0' id, 'ajkjd' NAME FROM DUAL WHERE NOT EXISTS (SELECT id FROM wms_abc WHERE wms_abc.id = '10e1041adf6311e98af2005056b6b8b0');




commit;