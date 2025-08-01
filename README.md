# notification-task
Service to delivery notifications


## Start application
This application starts as regular SpringBoot service
```mvn spring-boot:run``` 

## Go to User Interface 
local development  : http://localhost:8080/
It looks like -> ![UI_Screen.png](UI_Screen.png)

### Post a new message
- Fill the category and Message section
- Click on ```POST``` button
- If operation is success, the blue banner is show. Otherwise, a red banner is show
- Messages Posted will be displayed in ```Latest messages published``` section
- Check logs to see which users and how they were notified, each represent which third party service is going to use. Logs looks like: 
``` declarative
2025-08-01T14:42:26.711-06:00  INFO 23718 --- [notification] [nio-8080-exec-4] c.f.n.l.impl.EmailNotificationChannel    : Email to checo@mock.com: Someone has publish a message with category FINANCE, where body is The UK Supreme Court rules that lenders won't have to pay compensation to millions of motorists over car finance loans
2025-08-01T14:42:26.714-06:00  INFO 23718 --- [notification] [nio-8080-exec-4] c.f.n.l.impl.EmailNotificationChannel    : Email to mali@mock.com: Someone has publish a message with category FINANCE, where body is The UK Supreme Court rules that lenders won't have to pay compensation to millions of motorists over car finance loans
2025-08-01T14:42:26.714-06:00  INFO 23718 --- [notification] [nio-8080-exec-4] c.f.n.l.impl.PushNotificationChannel     : Push to Checo: Someone has publish a message with category FINANCE, where body is The UK Supreme Court rules that lenders won't have to pay compensation to millions of motorists over car finance loans
2025-08-01T14:42:26.715-06:00  INFO 23718 --- [notification] [nio-8080-exec-4] c.f.n.l.impl.SMSNotificationChannel      : SMS to 99-99-99-99-99: Someone has publish a message with category FINANCE, where body is The UK Supreme Court rules that lenders won't have to pay compensation to millions of motorists over car finance loans
2025-08-01T14:42:26.715-06:00  INFO 23718 --- [notification] [nio-8080-exec-4] c.f.n.l.impl.PushNotificationChannel     : Push to Mali: Someone has publish a message with category FINANCE, where body is The UK Supreme Court rules that lenders won't have to pay compensation to millions of motorists over car finance loans
```