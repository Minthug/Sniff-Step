'use client'
import React from 'react'
import { Locales } from '@/app/types/locales'
import useRegisterWalker from '@/app/hooks/useRegisterWalker'
import useFileChange from '@/app/hooks/useFileChange'
import { AiOutlineSearch } from 'react-icons/ai'
import DescriptionModal from './components/DescriptionModal'
import ChooseWalkDates from './components/ChooseWalkDates'
import ChooseWalkTimes from './components/ChooseWalkTimes'
import ChooseImageFile from './components/ChooseImageFile'
import DescriptionTextarea from './components/DescriptionTextarea'
import useResponsive from '@/app/hooks/useResponsive'
import { styles } from './resource'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const { file, fileSizeError, handleFileChange } = useFileChange()
    const {
        days,
        times,
        description,
        descriptionSizeError,
        descriptionExample,
        showDescriptionModal,
        handleDescriptionChange,
        handleDayChange,
        handleTimeChange,
        changeDayToKorean,
        changeTimeToKorean,
        setShowDescriptionModal
    } = useRegisterWalker()

    const { mobile } = useResponsive()

    if (mobile) {
        return (
            <div className="h-full min-h-screen">
                <div className="mb-4 border-b">
                    <input
                        className="w-full bg-[transparent] outline-none text-[20px] placeholder:text-[#d9d9d9]"
                        placeholder="ex) 문래동 댕댕이 산책 시켜드립니다 👍"
                        type="text"
                    />
                </div>
                <div>
                    <div className="mb-8">
                        <div className={styles.subject.mobile}>1. 본인을 표현할 수 있는 사진을 올려보세요!</div>
                        <ChooseImageFile file={file} handleFileChange={handleFileChange} />
                        {fileSizeError && (
                            <div className="text-[12px] text-[#ff0000]">파일 크기가 너무 큽니다. 100MB 이하로 업로드해주세요.</div>
                        )}
                    </div>
                    <div className="mb-8">
                        <div className={styles.subject.mobile}>2. 활동하실 주소를 정해주세요!</div>
                        <button className="relative w-full max-w-[480px] h-[40px] pr-8 text-[16px] text-start border-2 border-gray-300 rounded-md overflow-hidden text-ellipsis whitespace-nowrap">
                            <AiOutlineSearch className="absolute top-1/2 right-[4px] translate-y-[-50%] text-gray-400 text-[24px]" />
                        </button>
                    </div>
                    <div className="mb-8">
                        <div className={styles.subject.mobile}>3. 활동하실 요일을 정해주세요!</div>
                        <ChooseWalkDates days={days} lang={lang} handleDayChange={handleDayChange} changeDayToKorean={changeDayToKorean} />
                    </div>
                    <div className="mb-8">
                        <div className={styles.subject.mobile}>4. 산책이 가능한 시간대를 골라주세요!</div>
                        <ChooseWalkTimes
                            times={times}
                            lang={lang}
                            handleTimeChange={handleTimeChange}
                            changeTimeToKorean={changeTimeToKorean}
                        />
                    </div>
                    <div className="mb-8">
                        <div className={`${styles.subject.mobile} flex flex-col justify-between gap-2`}>
                            <div>5. 견주님들께 산책에 대한 경험 및 자세한 플랜을 설명해보세요!</div>
                            <button
                                onClick={() => setShowDescriptionModal(!showDescriptionModal)}
                                className="px-4 py-2 border rounded-sm bg-white text-[14px]"
                            >
                                예시 템플릿 가져오기
                            </button>
                        </div>
                        <DescriptionTextarea
                            description={description}
                            descriptionExample={descriptionExample}
                            descriptionSizeError={descriptionSizeError}
                            handleDescriptionChange={handleDescriptionChange}
                        />
                    </div>
                </div>
                {showDescriptionModal && (
                    <DescriptionModal
                        descriptionExample={descriptionExample}
                        setShowDescriptionModal={setShowDescriptionModal}
                        handleDescriptionChange={handleDescriptionChange}
                    />
                )}
                <div className="mb-8">
                    <button
                        className={`
                    w-full h-[60px] bg-green-900  text-[#fff] rounded-md
                    active:bg-green-800 mb-8
                `}
                    >
                        산책인 등록하기
                    </button>
                </div>
            </div>
        )
    }

    return (
        <div className="h-full min-h-screen">
            <div className="mb-8 border-b">
                <input
                    className="w-full bg-[transparent] outline-none text-[48px] placeholder:text-[#d9d9d9]"
                    placeholder="ex) 문래동 댕댕이 산책 시켜드립니다 👍"
                    type="text"
                />
            </div>
            <div>
                <div className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">1. 본인을 표현할 수 있는 사진을 올려보세요!</div>
                    <ChooseImageFile file={file} handleFileChange={handleFileChange} />
                    {fileSizeError && (
                        <div className="text-[12px] text-[#ff0000]">파일 크기가 너무 큽니다. 100MB 이하로 업로드해주세요.</div>
                    )}
                </div>
                <div className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">2. 활동하실 주소를 정해주세요!</div>
                    <button className="relative w-full max-w-[480px] h-[40px] pr-8 text-[16px] text-start border-2 border-gray-400 rounded-md overflow-hidden text-ellipsis whitespace-nowrap">
                        <AiOutlineSearch className="absolute top-1/2 right-[4px] translate-y-[-50%] text-[#898989] text-[24px]" />
                    </button>
                </div>
                <div className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">3. 활동하실 요일을 정해주세요!</div>
                    <ChooseWalkDates days={days} lang={lang} handleDayChange={handleDayChange} changeDayToKorean={changeDayToKorean} />
                </div>
                <div className="mb-8">
                    <div className="text-[18px] font-[500] mb-4">4. 산책이 가능한 시간대를 골라주세요!</div>
                    <ChooseWalkTimes
                        times={times}
                        lang={lang}
                        handleTimeChange={handleTimeChange}
                        changeTimeToKorean={changeTimeToKorean}
                    />
                </div>
                <div className="mb-8">
                    <div className="flex items-center justify-between text-[18px] font-[500] mb-4">
                        <div>5. 견주님들께 산책에 대한 경험 및 자세한 플랜을 설명해보세요!</div>
                        <button
                            onClick={() => setShowDescriptionModal(!showDescriptionModal)}
                            className="px-4 py-2 border rounded-sm bg-white"
                        >
                            예시 템플릿 가져오기
                        </button>
                    </div>
                    <DescriptionTextarea
                        description={description}
                        descriptionExample={descriptionExample}
                        descriptionSizeError={descriptionSizeError}
                        handleDescriptionChange={handleDescriptionChange}
                    />
                </div>
            </div>
            {showDescriptionModal && (
                <DescriptionModal
                    descriptionExample={descriptionExample}
                    setShowDescriptionModal={setShowDescriptionModal}
                    handleDescriptionChange={handleDescriptionChange}
                />
            )}
            <div className="mb-8">
                <button
                    className={`
                    w-full h-[60px] bg-green-900  text-[#fff] rounded-md
                    hover:bg-green-700 active:bg-green-800 mb-8
                `}
                >
                    산책인 등록하기
                </button>
            </div>
        </div>
    )
}
