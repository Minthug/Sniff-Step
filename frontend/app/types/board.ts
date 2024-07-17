export interface Board {
    id: string
    userId: number
    nickname: string
    title: string
    email: string
    description: string
    likeNumber: 0
    imagePath: string
    activityLocation: string
    activityDate: string[]
    activityTime: string[]
    createdAt: string
    updatedAt: string
    image: string
    profileUrl: string | null
}
