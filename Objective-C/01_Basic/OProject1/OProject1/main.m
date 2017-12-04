
#import <Foundation/Foundation.h>

@interface Person : NSObject{
    int m_age,m_id;
    NSString* m_name;
}
-(void)setAge:(int)age;
-(void)setId:(int)num;
-(void)setName:(NSString* )name;
-(void)showInfo;
-(id)init;
@end

@implementation Person
-(void)setAge:(int)age{
    m_age = age;
}
-(void)setId:(int)num{
    m_id = num;
}
-(void)setName:(NSString* )name{
    m_name = name;
}
-(void)showInfo{
    NSLog(@"我叫%@ 今年%d歲 ID:%d",m_name, m_age, m_id);
}
-(id)init{          //id 代表 回傳任意物件  不一定要叫init
    return [super init];
}
@end

int main(int argc, const char * argv[]) {
    
    Person *p = [[Person alloc] init];
    [p setName:@"賀振坤"];
    [p setAge:25];
    [p setId:31456];
    [p showInfo];

    return 0;
}
