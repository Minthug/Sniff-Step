import React from 'react'

export function BannerMobile() {
    return (
        <div
            className={`xl:hidden sm:px-24 relative h-[320px] flex flex-col justify-center items-center mt-[76px] px-4 bg-[#69d28c] overflow-hidden`}
        >
            <div className="z-10 select-none">
                <div
                    className={`
                            sm:text-[32px]
                            text-[32px] font-[700] text-white
                        `}
                >
                    동네에서 만나는
                </div>
                <div
                    className={`
                        sm:text-[54px]
                        text-[40px] font-[700] text-white
                    `}
                >
                    내 반려견 산책인
                </div>
                <div
                    className={`
                        sm:text-[18px]
                        text-[14px] text-white
                    `}
                >
                    우리 반려견들에게 필요한 산책인들과&nbsp;
                    <br className="sm:hidden" />
                    다양한 커넥션을 만들어보세요
                </div>
            </div>
            <div className="absolute top-[10px] right-[240px] w-[200px] h-[200px] bg-[#dcfbde] rounded-full" />
            <div className="absolute top-[50px] right-[200px] w-[100px] h-[100px] bg-[#d08a64] rounded-full" />
            <div className="absolute bottom-[-150px] right-[-250px] w-[500px] h-[500px] bg-[#32ac40] rounded-[50%] " />
            <div className="absolute top-[50px] right-[50px] w-[850px] h-[850px] bg-[#3d8345] rounded-[50%] " />
        </div>
    )
}
