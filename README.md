# my_simple_blog
very simple blog for myself,others just for browsing...

0，博客地址http://clmissfang.cn:8080/myBlog/article/index
1，使用的模板为thymeleaf，部署在阿里云服务器上，访问路径带项目名，图片上传路径及日志路径都为linux格式。所以在eclipse等下运行可能有小问题。
2，功能为博客展示及后台管理，游客只允许浏览及评论，留下主页或邮箱的游客才会保存。
3，使用jpa进行数据库(mysql)操作，为方便使用了很多本地查询，连接池为dbcp2。
4，集成markdown插件。
5，使用mail对评论进行邮件通知。
6，使用log4j2记录日志，数据库相关操作记录在单独日志文件。
7，前端使用boostrap栅格模式。
8，自定义拦截器对后台页面路径进行拦截处理。
9，后台登录使用session，浏览量采用基于ip和文章id的cookie，2小时以内同一ip算一次访问。
10，自定义工具类获取Ip，过滤xss攻击，校验网址、邮箱，日期处理等。
