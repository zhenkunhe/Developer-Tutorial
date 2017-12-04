
#import <Foundation/Foundation.h>
#import "ProAndSyn.h"

int main(int argc, const char * argv[]) {
    /*
    ProAndSyn* obj = [[ProAndSyn alloc] init];
    [obj setN_3:300];
    [obj setN_1:100];   //會自動產生set函式
    [obj setN_2:200];   //其中變數第一個字母大寫
    obj.n_2 = 2000;     //還有會內建Object.成員的call by value 方式
    
    NSLog(@"N_1:%d , N_2:%d , N_3:%d",obj.n_1,[obj n_2],[obj getN_3]);  //是[obj n_2],不是[obj getN_2]
    */
    
    /*
    NSNumber* num_1 = [NSNumber numberWithInt:20];  //基本數值的boxing型態,不用alloc
    NSNumber* num_2 = [NSNumber numberWithFloat:[num_1 floatValue]];    //unboxing
    NSNumber* num_3 = [NSNumber numberWithChar:'A'];
    
    NSLog(@"%d,%f,%c",[num_1 intValue],[num_2 floatValue],[num_3 charValue]);   //unboxing
    */
    
    NSString* str_1 = [NSString stringWithFormat: @"my first:%d" , 20];
    NSString* str_2 = [NSString stringWithFormat: @"my second:30"];
    NSString* str_3 = @"40";
    NSString* str_4 = [[NSString alloc] init];  //str_4 = ""
    NSString* str_5 = [[NSString alloc] initWithString:@"zhenkun"];  //str_5 = "zhenkun"
    
    NSLog(str_1);
    NSLog([str_2 uppercaseString]);
    NSLog(@"%@%d",[@"my third:" uppercaseString],[str_3 intValue]);
    return 0;
}