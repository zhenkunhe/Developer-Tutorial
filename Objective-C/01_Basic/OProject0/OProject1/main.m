
#import <Foundation/Foundation.h>

@interface MyObj : NSObject{
    int m_firNum;
    int m_secNum;
}
-(int)getFirNum;
-(void)setFirNum:(int)num;
-(int)getSecNum;
-(void)setSecNum:(int)num;
@end

@implementation MyObj
-(int)getFirNum{
    return m_firNum;
}
-(void)setFirNum:(int)num{
    m_firNum = num;
}
-(int)getSecNum{
    return m_secNum;
}
-(void)setSecNum:(int)num{
    m_secNum = num;
}
@end

int main(int argc, const char * argv[]) {

    //NSLog(@"Hello, World!");
    //printf("123\n");
    
    MyObj *obj = [[MyObj alloc] init];
    [obj setFirNum:20];
    NSLog(@"%i\n",obj.getFirNum);
    [obj setFirNum:30];
    NSLog(@"%i\n",obj.getFirNum);
    
    return 0;
}
